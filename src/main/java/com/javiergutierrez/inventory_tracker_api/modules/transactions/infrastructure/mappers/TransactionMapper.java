package com.javiergutierrez.inventory_tracker_api.modules.transactions.infrastructure.mappers;

import com.javiergutierrez.inventory_tracker_api.common.GenericMapper;
import com.javiergutierrez.inventory_tracker_api.modules.transactions.domain.Transaction;
import com.javiergutierrez.inventory_tracker_api.modules.transactions.infrastructure.entities.TransactionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper extends GenericMapper<TransactionEntity, Transaction> {
}
