package com.musinsa.admin.global.exception

class BadRequestException : RuntimeException {
    constructor(message: String) : super(message)
    constructor(errorCode: ErrorCodes) : super(errorCode.message)
}
