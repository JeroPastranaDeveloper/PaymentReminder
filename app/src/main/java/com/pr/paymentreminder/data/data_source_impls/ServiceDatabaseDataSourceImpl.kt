package com.pr.paymentreminder.data.data_source_impls

import com.pr.paymentreminder.base.CoroutineIO
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.data.model.toDomain
import com.pr.paymentreminder.data.model.toEntity
import com.pr.paymentreminder.data.room.service.ServiceDao
import com.pr.paymentreminder.data.source.ServiceDatabaseDataSource
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class ServiceDatabaseDataSourceImpl(
    private val serviceDao: ServiceDao,
    @CoroutineIO private val coroutineContext: CoroutineContext
) : ServiceDatabaseDataSource {
    override suspend fun clearAllServicesForm() =
        withContext(coroutineContext) {
            val allForms = serviceDao.getAllForms()
            allForms.orEmpty().forEach {
                serviceDao.deleteForm(it)
            }
        }

    override suspend fun getAllServicesForm(): List<Service>? =
        withContext(coroutineContext) {
            serviceDao.getAllForms()?.map { it.toDomain() }
        }

    override suspend fun getServiceForm(serviceId: String): Service =
        withContext(coroutineContext) {
            serviceDao.getServiceForm(serviceId).toDomain()
        }

    override suspend fun removeServiceForm(serviceId: String) =
        withContext(coroutineContext) {
            val service = serviceDao.getServiceForm(serviceId)
            serviceDao.deleteForm(service)
        }

    override suspend fun saveServiceForm(form: Service) =
        withContext(coroutineContext) {
            serviceDao.saveServiceRoom(form.toEntity())
        }
}