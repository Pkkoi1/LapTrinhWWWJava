package iuh.fit.edu.fontend.controllers;

import com.neovisionaries.i18n.CountryCode;
import iuh.fit.edu.backend.models.Candidate;
import iuh.fit.edu.backend.models.Job;
import iuh.fit.edu.backend.repositories.AddressRepository;
import iuh.fit.edu.backend.repositories.CandidateRepository;
import iuh.fit.edu.backend.repositories.JobRepository;
import iuh.fit.edu.backend.services.CandidateService;
import iuh.fit.edu.backend.services.JobService;
import org.slf4j.LoggerFactory;
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
public class CandidateController {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(CandidateController.class);
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private CandidateService candidateService;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private JobService jobService;


    @GetMapping("/candidates")
    public String showCandidateListPaging(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size, Optional<String> search) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<Candidate> candidatePage;
        if (search.isPresent() && !search.get().isEmpty()) {
            candidatePage = candidateService.findBySkill(search.get(), currentPage - 1, pageSize, "id", "asc");
            model.addAttribute("search", search.get());
        } else {
            candidatePage = candidateService.findAll(currentPage - 1, pageSize, "id", "asc");
        }
        model.addAttribute("candidatePage", candidatePage);

        int totalPages = candidatePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);

        }
        return "candidates/list";
    }


    @GetMapping("/candidates/show_edit_form/{id}")
    public ModelAndView edit(@PathVariable("id") long id) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Candidate> opt = candidateRepository.findById(id);
        if (opt.isPresent()) {
            Candidate candidate = opt.get();
            modelAndView.addObject("candidate", candidate);
            modelAndView.addObject("address", candidate.getAddress());
            List<CountryCode> sortedCountries = Arrays.stream(CountryCode.values())
                    .sorted(Comparator.comparing(CountryCode::getName))
                    .collect(Collectors.toList());
            modelAndView.addObject("country", sortedCountries);
            modelAndView.setViewName("candidates/edit");
        }

        return modelAndView;
    }

    @PostMapping("/candidates/update")
    public String updateCandidate(@ModelAttribute("candidate") Candidate candidate, BindingResult result, Model model) {
        addressRepository.save(candidate.getAddress());
        candidateRepository.save(candidate);
        return "redirect:/candidates";
    }

    @PostMapping("/candidates/save")
    public String saveCandidate(@ModelAttribute("candidate") Candidate candidate, BindingResult result, Model model) {
        addressRepository.save(candidate.getAddress());
        candidateRepository.save(candidate);
        return "redirect:/candidates";
    }

    @GetMapping("/candidates/show_add_form")
    public ModelAndView showAddForm() {
        ModelAndView modelAndView = new ModelAndView();
        Candidate candidate = new Candidate();
        modelAndView.addObject("candidate", candidate);
        modelAndView.addObject("country", CountryCode.values());
        modelAndView.setViewName("candidates/add");
        return modelAndView;
    }

    @GetMapping("/candidates/show_job_match/{id}")
    public String showJobMatch(@PathVariable("id") Long id, Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<Job> jobPage = jobService.findMatchingJobs(id, currentPage - 1, pageSize, "id", "asc");
        Candidate candidate = candidateRepository.findById(id).get();
        model.addAttribute("candidate", candidate);
        model.addAttribute("jobPage", jobPage);
        int totalPages = jobPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }


        return "jobs/jobMatching";
    }

}
