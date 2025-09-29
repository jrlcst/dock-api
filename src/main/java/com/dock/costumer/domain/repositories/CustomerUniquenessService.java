package com.dock.costumer.domain.repositories;

import com.dock.costumer.domain.vos.Document;

public interface CustomerUniquenessService {
    boolean existsByCpf(Document cpf);
}
