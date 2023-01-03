package store.gadgetspace.models.response

data class ApiResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T,
)
