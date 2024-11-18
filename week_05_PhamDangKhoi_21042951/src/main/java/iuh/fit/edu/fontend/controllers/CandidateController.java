package iuh.fit.edu.fontend.controllers;

import com.neovisionaries.i18n.CountryCode;
import iuh.fit.edu.backend.enums.skillLevel;
import iuh.fit.edu.backend.ids.CandidateSkillId;
import iuh.fit.edu.backend.models.Candidate;
import iuh.fit.edu.backend.models.CandidateSkill;
import iuh.fit.edu.backend.models.Job;
import iuh.fit.edu.backend.repositories.*;
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

import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/candidates")
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

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private CandidateSkillRepository candidateSkillRepository;


    @GetMapping(value = {"", "/"})
    public String showCandidateListPaging(Model model, @RequestParam("page") Optional<Integer> page,
                                          @RequestParam("size") Optional<Integer> size,
                                          Optional<String> search) {
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


    @GetMapping("/show_edit_form/{id}")
    public ModelAndView edit(@PathVariable("id") long id) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Candidate> opt = candidateRepository.findById(id);
        List<String> Skills = candidateService.suggestSkills(id);
        List<String> suggestSkills = new ArrayList<>();
        for (String skill : Skills) {
            if (!skill.equalsIgnoreCase(opt.get().getCandidateSkills().get(0).getSkill().getSkillName())) {
                suggestSkills.add(skill);
            }
        }
        if (opt.isPresent()) {
            Candidate candidate = opt.get();
            modelAndView.addObject("candidate", candidate);
            modelAndView.addObject("address", candidate.getAddress());
            List<CountryCode> sortedCountries = Arrays.stream(CountryCode.values())
                    .sorted(Comparator.comparing(CountryCode::getName))
                    .collect(Collectors.toList());
            modelAndView.addObject("country", sortedCountries);
            modelAndView.addObject("skills", suggestSkills);
            modelAndView.addObject("skillLevels", skillLevel.values());

            modelAndView.setViewName("candidates/edit");
        }
        return modelAndView;
    }

    @PostMapping("/update")
    public String updateCandidate(@ModelAttribute("candidate") Candidate candidate, BindingResult result, Model model) {
        addressRepository.save(candidate.getAddress());
        candidateRepository.save(candidate);
        return "redirect:/candidates";
    }

    @PostMapping("/candidates/save")
    public String saveCandidate(@ModelAttribute("candidate") Candidate candidate, BindingResult result, Model model) {
        candidate.getCandidateSkills().removeIf(candidateSkill -> candidateSkill.getSkill().getId() == null);
        addressRepository.save(candidate.getAddress());
        candidateRepository.save(candidate);
        for (CandidateSkill candidateSkill : candidate.getCandidateSkills()) {
            if (candidateSkill.getSkill() != null) {
                CandidateSkillId canSkID = new CandidateSkillId();
                canSkID.setCanId(candidate.getId());
                canSkID.setSkillId(candidateSkill.getSkill().getId());
                candidateSkill.setId(canSkID);
                candidateSkill.setCan(candidate);
            }
        }
        candidateSkillRepository.saveAll(candidate.getCandidateSkills());

        return "redirect:/candidates";
    }

    @GetMapping("/show_add_form")
    public ModelAndView showAddForm() {
        ModelAndView modelAndView = new ModelAndView();
        Candidate candidate = new Candidate();
        modelAndView.addObject("candidate", candidate);
        modelAndView.addObject("country", CountryCode.values());
        modelAndView.addObject("skills", skillRepository.findAll());
        modelAndView.addObject("skillLevels", skillLevel.values());

        modelAndView.setViewName("candidates/add");
        return modelAndView;
    }

    @GetMapping("/show_job_match/{id}")
    public String showJobMatch(@PathVariable("id") Long id, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        List<Job> jobs = jobService.findJobsForCandidate(id);
        Candidate candidate = candidateRepository.findById(id).get();
        System.out.println(jobs.size());
        model.addAttribute("candidate", candidate);
        model.addAttribute("jobs", jobs);
        return "jobs/jobMatching";
    }

    @GetMapping("/detail/{id}")
    public String showCandidateDetail(@PathVariable("id") Long id, Model model) {
        Optional<Candidate> candidate = candidateRepository.findById(id);

        if (candidate.isPresent()) {
            List<String> Skills = candidateService.suggestSkills(id);
            List<String> suggestSkills = new ArrayList<>();
            for (String skill : Skills) {
                if (!skill.equalsIgnoreCase(candidate.get().getCandidateSkills().get(0).getSkill().getSkillName())) {
                    suggestSkills.add(skill);
                }
            }
            Date dob = Date.from(candidate.get().getDob().atStartOfDay(ZoneId.systemDefault()).toInstant());
            model.addAttribute("dob", dob);
            model.addAttribute("candidate", candidate.get());
            model.addAttribute("skills", suggestSkills);

            return "candidates/details";
        }
        return "redirect:/candidates";
    }
}
