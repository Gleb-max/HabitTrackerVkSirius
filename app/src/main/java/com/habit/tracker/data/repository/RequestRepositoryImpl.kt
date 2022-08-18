package com.habit.tracker.data.repository

import com.habit.tracker.data.mapper.OrganizationResponseMapper
import com.habit.tracker.data.mapper.RequestResponseMapper
import com.habit.tracker.data.source.remote.api.RequestsApi
import com.habit.tracker.domain.entity.Geo
import com.habit.tracker.domain.entity.Organization
import com.habit.tracker.domain.entity.Request
import com.habit.tracker.domain.repository.RequestRepository
import javax.inject.Inject

class RequestRepositoryImpl @Inject constructor(
    private val remoteDataSource: RequestsApi,
    private val organizationsMapper: OrganizationResponseMapper,
    private val requestsMapper: RequestResponseMapper
) : RequestRepository {

    override suspend fun getOrganizations(): List<Organization> {
        val result = remoteDataSource.fetchOrganizations()
        return organizationsMapper.map(result)
//        return mockOrganization
    }

    override suspend fun getOrganizationById(organizationId: Int): Organization {
        val result = remoteDataSource.fetchOrganizationById(organizationId)
        return organizationsMapper.map(result)
//        return mockOrganization[organizationId]
    }

    override suspend fun getOrganizationRequests(organizationId: Int): List<Request> {
        val result = remoteDataSource.fetchOrganizationRequests(organizationId)
        return requestsMapper.map(result)
//        return mockRequest
    }

    override suspend fun getOrganizationRequestById(organizationId: Int, requestId: Int): Request {
        val result = remoteDataSource.fetchOrganizationRequestById(organizationId, requestId)
        return requestsMapper.map(result)
//        return mockRequest[requestId]
    }
}

//val mockOrganization = listOf(
//    Organization(
//        0,
//        "Организация 0",
//        "Пушкина, д. Колотушкина",
//        Geo(43.41459655761719, 39.95070362091973),
//    ),
//    Organization(
//        1,
//        "Организация 1",
//        "Пушкина, д. Колотушкина",
//        Geo(43.4145554, 39.950704),
//
//        ),
//    Organization(
//        2,
//        "Организация 2",
//        "Пушкина, д. Колотушкина",
//        Geo(43.414, 39.9507036),
//
//        ),
//    Organization(
//        3,
//        "Организация 3",
//        "Пушкина, д. Колотушкина",
//        Geo(43.41459655761719, 39.95070362091973),
//
//        ),
//    Organization(
//        4,
//        "Организация 4",
//        "Пушкина, д. Колотушкина",
//        Geo(43.5, 39.7)
//    ),
//)
//
//val mockRequest = listOf(
//    Request(
//        1,
//        "Без света никак!",
//        "Ночью просто не пройти, ведь ты даже не видишь куда идти. А если выйдет маньяк в бежевом плаще...",
//        true,
//        listOf("https://i.ytimg.com/vi/8-eWve5hL1U/maxresdefault.jpg")
//    ),
//    Request(
//        2,
//        "Без света никак!",
//        "Ночью просто не пройти, ведь ты даже не видишь куда идти. А если выйдет маньяк в бежевом плаще...",
//        true,
//        listOf("https://i.ytimg.com/vi/8-eWve5hL1U/maxresdefault.jpg")
//    ),
//    Request(
//        3,
//        "Без света никак!",
//        "Ночью просто не пройти, ведь ты даже не видишь куда идти. А если выйдет маньяк в бежевом плаще...",
//        true,
//        listOf("https://i.ytimg.com/vi/8-eWve5hL1U/maxresdefault.jpg")
//    ),
//    Request(
//        4,
//        "Без света никак!",
//        "Ночью просто не пройти, ведь ты даже не видишь куда идти. А если выйдет маньяк в бежевом плаще...",
//        true,
//        listOf("https://i.ytimg.com/vi/8-eWve5hL1U/maxresdefault.jpg")
//    ),
//)