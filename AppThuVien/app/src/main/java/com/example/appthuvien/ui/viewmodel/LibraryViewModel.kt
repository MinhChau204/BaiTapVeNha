package com.example.appthuvien.ui.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

// Tạo DataStore
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "library_data")

// Data class cho Book & User
data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val isBorrowed: Boolean = false
)

data class User(
    val id: Int,
    val name: String,
    val borrowedBooks: List<Book> = emptyList()
)

class LibraryViewModel(application: Application) : AndroidViewModel(application) {

    // Dữ liệu
    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books = _books.asStateFlow()

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users = _users.asStateFlow()

    // Thông báo
    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    // Giới hạn số sách một người có thể mượn
    private val maxBorrowedBooks = 3

    // Khởi tạo Gson để chuyển đổi dữ liệu thành JSON
    private val gson = Gson()

    // Keys để lưu dữ liệu vào DataStore
    companion object {
        private val BOOKS_KEY = stringPreferencesKey("books_key")
        private val USERS_KEY = stringPreferencesKey("users_key")
    }

    init {
        // Đọc dữ liệu từ DataStore khi khởi tạo
        viewModelScope.launch {
            loadDataFromDataStore()
        }
    }

    // Khởi tạo dữ liệu mẫu nếu không có dữ liệu trong DataStore
    private fun initData() {
        val initialBooks = listOf(
            Book(1, "Oliver Twist", "Charles Dickens"),
            Book(2, "Đắc Nhân Tâm", "Dale Carnegie"),
            Book(3, "Macbeth", "William Shakespeare"),
            Book(4, "The Tempest", "William Shakespeare"),
            Book(5, "Goldfinger", "Ian Fleming"),
            Book(6, "The Mill on the Floss", "George Eliot"),
            Book(7, "Án mạng trên sông Nile", "Agatha Christie"),
            Book(8, "Thế giới thất lạc", "Arthur Conan Doyle"),
            Book(9, "Nhà Giả Kim", "Paulo Coelho"),
            Book(10, "Đọc Vị Bất Kỳ Ai", "David J.Lieberman"),
            Book(11, "Tiểu thuyết Bố Già", "Mario Puzo"),
            Book(12, "Tội Ác Và Hình Phạt", "Fyodor Dostoevsky"),
        )

        val initialUsers = listOf(
            User(1, "Bích Nhân"),
            User(2, "Bảo Châu"),
            User(3, "Hoàng My"),
            User(4, "Xuân Khanh"),
            User(5, "Minh Châu"),
            User(6, "Hải Đăng"),
            User(7, "Như Quỳnh")

        )

        _books.value = initialBooks
        _users.value = initialUsers

        // Lưu dữ liệu mẫu vào DataStore
        viewModelScope.launch {
            saveDataToDataStore()
        }
    }

    // Đọc dữ liệu từ DataStore
    private suspend fun loadDataFromDataStore() {
        val context = getApplication<Application>().applicationContext
        val dataStore = context.dataStore

        val booksJson = dataStore.data.first()[BOOKS_KEY] ?: ""
        val usersJson = dataStore.data.first()[USERS_KEY] ?: ""

        if (booksJson.isEmpty() || usersJson.isEmpty()) {
            // Nếu không có dữ liệu, khởi tạo dữ liệu mẫu
            initData()
        } else {
            // Chuyển đổi JSON thành danh sách Book và User
            val bookType = object : TypeToken<List<Book>>() {}.type
            val userType = object : TypeToken<List<User>>() {}.type

            val loadedBooks: List<Book> = gson.fromJson(booksJson, bookType) ?: emptyList()
            val loadedUsers: List<User> = gson.fromJson(usersJson, userType) ?: emptyList()

            _books.value = loadedBooks
            _users.value = loadedUsers
        }
    }

    // Lưu dữ liệu vào DataStore
    private suspend fun saveDataToDataStore() {
        val context = getApplication<Application>().applicationContext
        val dataStore = context.dataStore

        dataStore.edit { preferences ->
            val booksJson = gson.toJson(_books.value)
            val usersJson = gson.toJson(_users.value)
            preferences[BOOKS_KEY] = booksJson
            preferences[USERS_KEY] = usersJson
        }
    }

    // Mượn sách
    fun borrowBook(userName: String, bookTitle: String) {
        viewModelScope.launch {
            try {
                val user = findUserByName(userName)
                val book = findBookByTitle(bookTitle)

                when {
                    user == null -> _message.value = "Không tìm thấy người dùng: $userName"
                    book == null -> _message.value = "Không tìm thấy sách: $bookTitle"
                    user.borrowedBooks.size >= maxBorrowedBooks -> _message.value = "Người dùng đã mượn tối đa $maxBorrowedBooks sách!"
                    book.isBorrowed -> _message.value = "Sách '$bookTitle' đã được mượn!"
                    else -> {
                        val updatedBook = book.copy(isBorrowed = true)
                        val updatedBorrowedBooks = user.borrowedBooks.toMutableList().apply { add(updatedBook) }
                        val updatedUser = user.copy(borrowedBooks = updatedBorrowedBooks)

                        _users.value = _users.value.map { if (it.id == user.id) updatedUser else it }
                        _books.value = _books.value.map { if (it.id == book.id) updatedBook else it }
                        _message.value = "Mượn sách '$bookTitle' thành công!"
                        resetMessageAfterDelay()

                        // Lưu dữ liệu vào DataStore sau khi cập nhật
                        saveDataToDataStore()
                    }
                }
            } catch (e: Exception) {
                _message.value = "Đã xảy ra lỗi: ${e.message}"
                Log.e("LibraryViewModel", "Error borrowing book", e)
            }
        }
    }

    // Trả sách
    fun returnBook(userName: String, bookTitle: String) {
        viewModelScope.launch {
            try {
                val user = findUserByName(userName)
                val book = findBookByTitle(bookTitle)

                when {
                    user == null -> _message.value = "Không tìm thấy người dùng: $userName"
                    book == null -> _message.value = "Không tìm thấy sách: $bookTitle"
                    !book.isBorrowed -> _message.value = "Sách '$bookTitle' chưa được mượn!"
                    user.borrowedBooks.find { it.id == book.id } == null -> _message.value = "Người dùng không mượn sách '$bookTitle'!"
                    else -> {
                        val updatedBook = book.copy(isBorrowed = false)
                        val updatedBorrowedBooks = user.borrowedBooks.toMutableList().apply { removeIf { it.id == book.id } }
                        val updatedUser = user.copy(borrowedBooks = updatedBorrowedBooks)

                        _users.value = _users.value.map { if (it.id == user.id) updatedUser else it }
                        _books.value = _books.value.map { if (it.id == book.id) updatedBook else it }
                        _message.value = "Trả sách '$bookTitle' thành công!"
                        resetMessageAfterDelay()

                        // Lưu dữ liệu vào DataStore sau khi cập nhật
                        saveDataToDataStore()
                    }
                }
            } catch (e: Exception) {
                _message.value = "Đã xảy ra lỗi: ${e.message}"
                Log.e("LibraryViewModel", "Error returning book", e)
            }
        }
    }

    // Tìm kiếm sách theo tiêu đề hoặc tác giả
    fun searchBooks(query: String) {
        val filteredBooks = if (query.isBlank()) {
            _books.value
        } else {
            _books.value.filter { it.title.contains(query, ignoreCase = true) ||
                    it.author.contains(query, ignoreCase = true) }
        }
        _books.value = filteredBooks
        viewModelScope.launch {
            saveDataToDataStore()
        }
    }

    // Lọc người dùng theo tên
    fun filterUsersByName(query: String) {
        val filteredUsers = if (query.isBlank()) {
            _users.value
        } else {
            _users.value.filter { it.name.contains(query, ignoreCase = true) }
        }
        _users.value = filteredUsers
        viewModelScope.launch {
            saveDataToDataStore()
        }
    }

    // Reset dữ liệu về trạng thái ban đầu
    fun resetData() {
        initData()
    }

    // Tự động xóa thông báo sau 3 giây
    private fun resetMessageAfterDelay() {
        viewModelScope.launch {
            delay(3000)
            _message.value = ""
        }
    }

    // Xóa thông báo thủ công
    fun clearMessage() {
        _message.value = ""
    }

    private fun findUserByName(userName: String): User? = _users.value.find { it.name.trim().lowercase() == userName.trim().lowercase() }
    private fun findBookByTitle(bookTitle: String): Book? = _books.value.find { it.title.trim().lowercase() == bookTitle.trim().lowercase() }
}