package emojifetcher

interface EmojiFetchCallback {
    fun onFetchSuccess(
        data: String,
    )

    fun onFetchFailure(
        errorMessage: String,
    )
}
