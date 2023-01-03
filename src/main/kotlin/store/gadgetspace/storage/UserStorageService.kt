package store.gadgetspace.storage

import store.gadgetspace.models.User

class UserStorageService : UserDataStorage {
    private val users = mutableListOf<User>()
    override fun addUser(user: User): Boolean {
        return users.add(user)
    }

    override fun findUserByEmail(email: String): User? {
        return users.find {
            it.email == email
        }
    }

    override fun getAllUsers(): List<User> {
        return users.toList()
    }
}