package com.prep.core.domain.usecase

interface UseCase<out Type, in Params> {

    suspend operator fun invoke(input: Params): Type
}

class None