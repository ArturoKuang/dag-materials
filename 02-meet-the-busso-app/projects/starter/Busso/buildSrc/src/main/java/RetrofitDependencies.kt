fun retrofitDeps(
  retrofit: Boolean = true,
  gsonconverter: Boolean = false,
  rx2converter: Boolean = false
): List<String> = mutableListOf<String>().apply {
  if (retrofit) {
    add("com.squareup.retrofit2:retrofit:2.9.0")
    add("com.squareup.okhttp3:logging-interceptor:4.9.1")
  }
  if (gsonconverter) {
    add("com.squareup.retrofit2:converter-gson:2.9.0")
  }
  if (rx2converter) {
    add("com.squareup.retrofit2:adapter-rxjava2:2.9.0")
  }
}
