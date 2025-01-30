package com.musinsa.admin.global.exception

enum class ErrorCodes(val code: String, val message: String) {
    GLOBAL_ERROR("GLOBAL_001", "요청 처리 중 오류가 발생했습니다. 페이지를 새로 고치거나 잠시 후 다시 시도해 주세요."),
    INVALID_PARAMETER_VALUE("GLOBAL_002", "요청하신 값이 올바르지 않습니다."),
    MISSING_REQUIRED_FIELD("GLOBAL_003", "필수 값이 입력되지 않았습니다."),
    VALIDATION_FAILED("GLOBAL_004", "입력 값이 올바르지 않습니다."),
    BAD_REQUEST("GLOBAL_005", "잘못된 요청입니다."),
    CONSTRAINT_VIOLATION("GLOBAL_006", "제약 조건을 위반했습니다."),
    CATEGORY_NOT_FOUND("CATEGORY_001", "카테고리를 찾을 수 없습니다."),
    BRAND_NOT_FOUND("BRAND_001", "브랜드를 찾을 수 없습니다."),
    PRODUCT_NOT_FOUND("PRODUCT_001", "상품을 찾을 수 없습니다."),
}
