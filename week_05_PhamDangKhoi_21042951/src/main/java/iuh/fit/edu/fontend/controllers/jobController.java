package iuh.fit.edu.fontend.controllers;


import iuh.fit.edu.backend.enums.skillLevel;
import iuh.fit.edu.backend.enums.skillType;
import iuh.fit.edu.backend.ids.JobSkillId;
import iuh.fit.edu.backend.models.*;
import iuh.fit.edu.backend.repositories.*;
import iuh.fit.edu.backend.services.CandidateService;
import iuh.fit.edu.backend.services.CompanyService;
import iuh.fit.edu.backend.services.EmailService;
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
    private EmailService emailService;
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private JobSkillRepository jobSkillRepository;

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
        ModelAndView mav = new ModelAndView("jobs/add");
        Job job = new Job();
        mav.addObject("company", companyRespository.findById(id).get());
        mav.addObject("skills", skillRepository.findAll());
        mav.addObject("skillLevels", skillLevel.values());
        mav.addObject("job", job);
        return mav;
    }

    @PostMapping("/save/{id}")
    public String saveJob(@PathVariable("id") Long companyId, @ModelAttribute("job") Job job, BindingResult result, Model model) {
        job.getJobSkills().removeIf(jobSkill -> jobSkill.getSkill().getId() == null);
        Optional<Company> companyOptional = companyRespository.findById(companyId);
        if (companyOptional.isPresent()) {
//            job.setCompany(companyOptional.get());
            Job job1 = new Job();
            job1.setJobSkills(job.getJobSkills());
            job1.setCompany(companyOptional.get());
            job1.setJobName(job.getJobName());
            job1.setJobDesc(job.getJobDesc());
            jobRepository.save(job1);
            for (JobSkill jobSkill : job1.getJobSkills()) {
                if (jobSkill.getSkill().getId() != null) {
                    JobSkillId jobSkillId = new JobSkillId();
                    jobSkillId.setJobId(job1.getId());
                    jobSkillId.setSkillId(jobSkill.getSkill().getId());
                    jobSkill.setId(jobSkillId);
                    jobSkill.setJob(job1);
                }
            }
            jobSkillRepository.saveAll(job1.getJobSkills());
        }
        return "redirect:/jobs/{id}";
    }

    @GetMapping("/show_candidate_matching/{id}/{jobID}")
    public String showCandidateMatching(Model model, @PathVariable("id") Long id,
                                        @PathVariable("jobID") Long jobID,
                                        Optional<String> search) {

        Company company = companyRespository.findById(id).get();

        model.addAttribute("company", company);
        model.addAttribute("job", jobRepository.findById(jobID).get());
        if (search.isPresent()) {
            List<Candidate> candidates = candidateService.findMatchingCandidatesByKey(jobID, search.get());
            model.addAttribute("candidates", candidates);
            return "jobs/CandidateMatching";
        } else {
            List<Candidate> candidates = candidateService.findMatchingCandidates(jobID);
            model.addAttribute("candidates", candidates);
            return "jobs/CandidateMatching";
        }
    }

    @PostMapping("/{id}/{jobId}/{candidateId}/send-email")
    public String sendEmail(@PathVariable("jobId") Long jobId, @PathVariable("candidateId") Long candidateId, Model model) {
        Job job = jobService.findById(jobId);
        Candidate candidate = candidateRepository.findById(candidateId).get();
        emailService.sendEmail(candidate, job);
        return "redirect:/jobs/{id}";
    }
}
