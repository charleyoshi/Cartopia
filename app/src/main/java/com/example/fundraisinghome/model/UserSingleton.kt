package com.example.fundraisinghome.model

import com.example.fundraisinghome.ui.theme.CartViewModel

// UserSingleton.kt

object UserSingleton {
    private var currentUser: User? = null
    private val users: MutableList<User> = mutableListOf()
    private val orders: MutableList<Order> = mutableListOf()
    private var cartViewModel: CartViewModel? = null

    fun setCartViewModel(viewModel: CartViewModel) {
        cartViewModel = viewModel
    }

    // Function to log in the user
    fun loginUser(username: String, password: String): Boolean {
        // Find the user with the provided username and password
        val user = users.find { it.username == username && it.password == password }
        if (user != null) {
            // Set the current user
            setCurrentUser(user)
            return true // Login successful
        }
        return false // Login failed
    }

    fun logoutUser()  {
        currentUser = null
    }

    // Function to register a new user
    fun registerUser(username: String, password: String): Boolean {
        // Perform registration logic here (e.g., store in database)
        // For demonstration, let's assume registration is successful
        if (users.any { it.username == username }) {
            return false // User with the same username already exists
        }

        // Add the new user to the list
        val newUser = User(userId = users.size.toString(), username = username, password = password)
        users.add(newUser)
        setCurrentUser(newUser)
        return true
    }

    // Function to get the current user
    fun getCurrentUser(): User? {
        return currentUser
    }
    fun setCurrentUser(user: User?) {
        currentUser = user
        cartViewModel?.updateUiState() // Call resetCart() whenever the current user changes
    }

    // Function to add an order
    fun addOrder(order: Order) {
        order.orderId = (orders.size + 1).toString()
        orders.add(order)
    }

    // Function to retrieve orders for the current user
    fun retrieveOrdersForCurrentUser(): List<Order> {
        val currentUser = getCurrentUser()
        if (currentUser != null) {
            // Filter orders for the current user
            return orders.filter { it.userId == currentUser.userId }
        }
        return emptyList() // Return empty list if no current user is logged in
    }


}
