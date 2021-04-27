package com.emamagic.moviestreaming.repository.home

import com.emamagic.moviestreaming.base.upsert
import com.emamagic.moviestreaming.db.dao.SliderDao
import com.emamagic.moviestreaming.db.entity.SliderEntity
import com.emamagic.moviestreaming.network.HomeApi
import com.emamagic.moviestreaming.network.dto.SliderDto
import com.emamagic.moviestreaming.safe.GeneralErrorHandlerImpl
import com.emamagic.moviestreaming.safe.ResultWrapper
import com.emamagic.moviestreaming.safe.toResult
import com.emamagic.moviestreaming.util.performOperation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeApi: HomeApi,
    private val sliderDao: SliderDao,
    private val sliderDot: SliderDto
) : GeneralErrorHandlerImpl(), HomeRepository {


    override fun getSliders(): Flow<ResultWrapper<List<SliderEntity>>> {
        return performOperation(
            databaseQuery = { sliderDao.getAllSlider().toResult(this) },
            networkCall = { homeApi.getSliders().toResult(this) },
            saveCallResult = { sliderDao.upsert(sliderDot.mapFromEntityList(it.sliders)) }
        )
    }


}