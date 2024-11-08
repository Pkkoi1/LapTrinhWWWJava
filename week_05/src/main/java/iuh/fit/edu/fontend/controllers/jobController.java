package iuh.fit.edu.fontend.controllers;


import iuh.fit.edu.backend.enums.skillLevel;
import iuh.fit.edu.backend.enums.skillType;
import iuh.fit.edu.backend.models.*;
import iuh.fit.edu.backend.repositories.*;
import iuh.fit.edu.backend.services.CandidateService;
import iuh.fit.edu.backend.services.CompanyService;
import iuh.fit.edu.backend.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/jobs")
public class jobController {
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private CandidateService candidateService;
    @Autowired
    private JobService jobService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private CompanyRespository companyRespository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private SkillRepository skillRepository;

    @GetMapping("/{id}")
    public String showListCompany(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size, @PathVariable("id") Long id) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<Job> jobPage = jobService.companyJobs(id, currentPage - 1, pageSize, "id", "asc");
        Company company = companyRespository.findById(id).get();

        model.addAttribute("company", company);
        model.addAttribute("jobPage", jobPage);
        int totalPages = jobPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "jobs/list";
    }

    @GetMapping("/show_add_form/{id}")
    public ModelAndView showAddForm(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView();
        Optional<Company> company = companyRespository.findById(id);
        Job job = new Job();
        // Initialize jobSkills with empty JobSkill objects
        for (int i = 0; i < 5; i++) {
            JobSkill jobSkill = new JobSkill();
            jobSkill.setJob(job);
            job.getJobSkills().add(jobSkill);
        }

        List<Skill> skills = skillRepository.findAll().stream()
                .sorted(Comparator.comparing(Skill::getSkillName))
                .collect(Collectors.toList());
        mav.addObject("skills", skills);
        mav.addObject("job", job);

        if (company.isPresent()) {
            Company company1 = company.get();
            mav.addObject("company", company1);
            List<skillLevel> skillLevels = Arrays.stream(skillLevel.values())
                    .collect(Collectors.toList());

            List<skillType> skillTypes = Arrays.stream(skillType.values())
                    .collect(Collectors.toList());
            mav.addObject("skillTypes", skillTypes);
            mav.addObject("skillLevels", skillLevels);
            mav.setViewName("jobs/add");
        }
        return mav;
    }

    @PostMapping("/save/{id}")
    public String saveJob(@PathVariable("id") Long companyId, @ModelAttribute("job") Job job, BindingResult result, Model model) {
        Optional<Company> companyOptional = companyRespository.findById(companyId);
        if (companyOptional.isPresent()) {
            job.setCompany(companyOptional.get());
            jobRepository.save(job);
        }
        return "redirect:/jobs/{id}";
    }

    @GetMapping("/show_candidate_matching/{id}/{jobID}")
    public String showCandidateMatching(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size, @PathVariable("id") Long id, @PathVariable("jobID") Long jobID, Optional<String> search) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        Company company = companyRespository.findById(id).get();

        model.addAttribute("company", company);

        Page<Candidate> candidatePage;
        if (search.isPresent() && !search.get().isEmpty()) {
            candidatePage = candidateService.findBySkill(search.get(), currentPage - 1, pageSize, "id", "asc");
            model.addAttribute("search", search.get());
        } else {
            candidatePage = candidateService.findMatchingCandidates(jobID, currentPage - 1, pageSize, "id", "asc");
        }
        model.addAttribute("candidatePage", candidatePage);

        int totalPages = candidatePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);

        }
        return "jobs/CandidateMatching";
    }
}
