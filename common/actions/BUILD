load(
    "//:build-visibility.bzl",
    "COMMON_PLUGINS_VISIBILITY",
)

licenses(["notice"])  # Apache 2.0

java_library(
    name = "actions",
    srcs = glob(["src/**/*.java"]),
    visibility = COMMON_PLUGINS_VISIBILITY,
    deps = [
        "//intellij_platform_sdk:jsr305",
        "//intellij_platform_sdk:plugin_api",
        "//sdkcompat",
    ],
)
