package store.gadgetspace.storage

import store.gadgetspace.models.User

interface UserDataStorage {
    fun addUser(user: User) : Boolean
    fun findUserByEmail (email: String) : User?
    fun getAllUsers () : List<User>

}