package omkar.android.projects.data.local.model

import kotlinx.serialization.Serializable

@Serializable
data class ExampleRequest(
    val exampleRequest: String = ""
)

@Serializable
data class ExampleResponse(
    val exampleText: String = ""
)
