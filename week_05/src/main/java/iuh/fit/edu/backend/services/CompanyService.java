package iuh.fit.edu.backend.services;

import iuh.fit.edu.backend.models.Company;
import iuh.fit.edu.backend.repositories.CompanyRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service

public class CompanyService {

    @Autowired
    private CompanyRespository companyRespository;

    public Page<Company> findAll(int page, int size, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return companyRespository.findAll(pageable);
    }

    public Page<Company> findByKey(String key, int page, int size, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return companyRespository.findByKey(key, pageable);

    }
}
