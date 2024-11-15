package iuh.fit.edu.backend.services;

import iuh.fit.edu.backend.models.Candidate;
import iuh.fit.edu.backend.models.Job;
import iuh.fit.edu.backend.models.JobSkill;
import iuh.fit.edu.backend.repositories.CandidateRepository;
import iuh.fit.edu.backend.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CandidateService {
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private JobService jobService;

    public Page<Candidate> findAll(int page, int size, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return candidateRepository.findAll(pageable);
    }

    public Page<Candidate> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Candidate> list;
        List<Candidate> candidates = candidateRepository.findAll();

        if (candidates.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, candidates.size());
            list = candidates.subList(startItem, toIndex);
        }
        Page<Candidate> candidatePage = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), candidates.size());
        return candidatePage;
    }

    public Page<Candidate> findBySkill(String skill, int page, int size, String sortBy, String sortDirection) {
        String skillName = skill.toLowerCase();
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        return candidateRepository.findByKey(skillName, pageable);
    }

    public List<Candidate> findMatchingCandidates(Long jobId) {
        Job job = jobService.findById(jobId);
        return job.getJobSkills().stream()
                .map(jobSkill -> candidateRepository.findMatchingCandidates(jobSkill.getSkillLevel(), jobSkill.getSkill().getSkillName()))
                .flatMap(List::stream)
                .toList();
    }

    public List<Candidate> findMatchingCandidatesByKey(Long jonId, String key) {
        Job job = jobService.findById(jonId);
        return job.getJobSkills().stream()
                .map(jobSkill -> candidateRepository.findMatchingCandidatesByKey(jobSkill.getSkillLevel(), jobSkill.getSkill().getSkillName(), key))
                .flatMap(List::stream)
                .toList();
    }

}