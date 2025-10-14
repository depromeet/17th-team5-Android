# Data Model Generation Prompt

## Objective

Based on the JSON response provided, generate all necessary data models and mappers across the relevant modules. Adhere strictly to the project's Clean Architecture and multi-module structure as defined in `project/ai/README.md`.

## Instructions

You will be given a JSON response. Your task is to create the corresponding data classes and mappers across 5 different modules, following the precise structure, naming conventions, and file paths shown in the examples below.

**Important Rules**: The generated models must **exactly** follow the structure of the examples below.
1.  Do not create generic wrapper classes like `BaseResponse<T>`. Each `...Response` DTO must directly include the `code`, `message`, and `data` fields, as shown in the `SearchResponse` example.
2.  When implementing mappers, do not use the unnecessary `this` keyword.

---

### Generation Steps

Follow these 5 steps to generate the files for each layer.

#### Step 1: `:core:retrofit:model`

-   **Purpose**: Create the DTO that directly maps to the JSON response. This is the entry point for the data.
-   **Annotations**: Use `@Serializable` from `kotlinx.serialization`.
-   **Mapper**: Implement the `RetrofitMapper<T>` interface to convert the DTO to the `RemoteData` model.

**Example (`SearchResponse.kt`):**
```kotlin
data class SearchResponse(
    val code: String,
    val message: String,
    val data: List<SearchInfoResponse>
) : RetrofitMapper<SearchRemoteData> {

    override fun toRemoteData(): SearchRemoteData = SearchRemoteData(
        code = code,
        message = message,
        data = data.map { it.toRemoteData() }
    )
}

data class SearchInfoResponse(
    val market: String,
    val code: String,
    val companyName: String
) : RetrofitMapper<SearchInfoRemoteData> {
    override fun toRemoteData(): SearchInfoRemoteData = SearchInfoRemoteData(
        market = market,
        symbol = code,
        title = companyName
    )
}
```

#### Step 2: `:core:remotedatasource:model`

-   **Purpose**: Define the data model for the remote data source layer.
-   **Mapper**: Implement the `RemoteDataMapper<T>` interface to convert the `RemoteData` model to the `Data` model.

**Example (`SearchRemoteData.kt`):**
```kotlin
data class SearchRemoteData(
    val code: String,
    val message: String,
    val data: List<SearchInfoRemoteData>
) : RemoteDataMapper<SearchData> {

    override fun toData(): SearchData = SearchData(
        code = code,
        message = message,
        data = data.map { it.toData() }
    )
}

data class SearchInfoRemoteData(
    val market: String,
    val symbol: String,
    val title: String
) : RemoteDataMapper<SearchInfoData> {

    override fun toData(): SearchInfoData = SearchInfoData(
        market = market,
        symbol = symbol,
        title = title
    )
}
```

#### Step 3: `:core:data:model`

-   **Purpose**: Define the data model for the data repository layer.
-   **Mapper**: Implement the `DataMapper<T>` interface to convert the `Data` model to the `Domain` model.

**Example (`SearchData.kt`):**
```kotlin
data class SearchData(
    val code: String,
    val message: String,
    val data: List<SearchInfoData>
) : DataMapper<Search> {

    override fun toDomain(): Search = Search(
        code = code,
        message = message,
        data = data.map { it.toDomain() }
    )
}

data class SearchInfoData(
    val market: String,
    val symbol: String,
    val title: String
) : DataMapper<SearchInfo> {

    override fun toDomain(): SearchInfo = SearchInfo(
        market = market,
        symbol = symbol,
        title = title
    )
}
```

#### Step 4: `:core:domain:model`

-   **Purpose**: Define the pure business model. This class should be clean and contain no Android framework dependencies or mapping logic.

**Example (`Search.kt`):**
```kotlin
data class Search(
    val code: String,
    val message: String,
    val data: List<SearchInfo>
)

data class SearchInfo(
    val market: String,
    val symbol: String,
    val title: String
)
```
