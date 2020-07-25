package com.saiful.moviestvseries.util


interface EntityMapper <EntityModel, DomainModel>{

    fun mapFromEntity(entity: EntityModel) : DomainModel

}