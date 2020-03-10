package com.roslonek.contracts.controllers;

import com.roslonek.contracts.entities.UserDetails;
import com.roslonek.contracts.entities.Worker;
import com.roslonek.contracts.repositories.UserDetailsRepository;
import com.roslonek.contracts.repositories.UserRepository;
import com.roslonek.contracts.repositories.WorkerRepository;
import com.roslonek.contracts.services.WorkerService;
import com.roslonek.contracts.util.EmailUtilImpl;
import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class WorkerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerController.class);

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Autowired
    WorkerRepository workerRepository;

    @Autowired
    WorkerService workerService;

    @Autowired
    private EmailUtilImpl emailUtil;


    @RequestMapping("/showMenu")
    public String showMenu(@RequestParam("id") int id, ModelMap modelMap) {
        UserDetails userDetails = userDetailsRepository.findByUserId(id);


        modelMap.addAttribute("userDetails", userDetails);


        return "menu";
    }

    @RequestMapping("/showActive")
    public String showActive(@RequestParam("id") int id, ModelMap modelMap) {
        UserDetails userDetails = userDetailsRepository.findByUserId(id);
        modelMap.addAttribute("userDetails", userDetails);

        List<Worker> workers = workerRepository.findWorkers(id, true);

        Collections.sort(workers, new NameComparator());


        modelMap.addAttribute("workers", workers);
        int i = 0;
        modelMap.addAttribute("i", i);

        return "activeWorkers";

    }

    @RequestMapping("/showAddNew")
    public String showAddNew(@RequestParam("id") int id, ModelMap modelMap) {
        UserDetails userDetails = userDetailsRepository.findByUserId(id);
        modelMap.addAttribute("userDetails", userDetails);
        return "addNew";
    }


    @RequestMapping("/addNew")
    public String addNew(@RequestParam("idi") int idi, ModelMap modelMap, @ModelAttribute("worker") Worker worker) {
        UserDetails userDetails = userDetailsRepository.findByUserId(idi);
        modelMap.addAttribute("userDetails", userDetails);
        workerRepository.save(worker);
        String msg = "pracownik dodany";
        modelMap.addAttribute("msg", msg);

        return "addNew";
    }

    @RequestMapping("/showUpdateWorker")
    public String showUpdateWorker(@RequestParam("id") int id, ModelMap modelMap) {
        Worker worker = workerRepository.findById(id).get();
        modelMap.addAttribute("worker", worker);
        return "updateWorker";
    }

    @RequestMapping("/updateWorker")
    public String showAddNew(@RequestParam("idi") int idi, @ModelAttribute("worker") Worker worker, ModelMap modelMap) {


        workerRepository.save(worker);
        modelMap.addAttribute("worker", worker);
        String msg = "dane zmienione";
        modelMap.addAttribute("msg", msg);

        return "updateWorker";
    }

    @RequestMapping("/deactivateWorker")
    public String deactivateWorker(@RequestParam("id") int idi, ModelMap modelMap) {

        workerRepository.findById(idi).get().setActive(false);

        Worker worker = workerRepository.findById(idi).get();
        workerRepository.save(worker);

        return showActive(worker.getUserId(), modelMap);

    }

    @RequestMapping("/deactivateAll")
    public String deactivateAll(@RequestParam("id") int idi, ModelMap modelMap) {


        workerService.deactivateAllWorkers(idi);

        return showActive(idi, modelMap);

    }

    @RequestMapping("/searchWorker")
    public String searchWorker(@RequestParam("search") String search, @RequestParam("userId") int userId, ModelMap modelMap) {


        List<Worker> workersSearch = workerService.searchWorker(userId, search);
        Collections.sort(workersSearch, new NameComparator());

        modelMap.addAttribute("workersSearch", workersSearch);
        modelMap.addAttribute("userId", userId);


        return "activeWorkerSearch";

    }

    @RequestMapping("/addSearchedWorker")
    public String addSearchedWorker(@RequestParam("id") int workerId, ModelMap modelMap) {

        workerRepository.findById(workerId).get().setActive(true);

        Worker worker = workerRepository.findById(workerId).get();
        workerRepository.save(worker);


        String msg = "pracownik dodany, dodaj następnego, by wyszukać szybko ze wszystkich naciśniej jednocześnie ctr + f";
        modelMap.addAttribute("msg", msg);


        return searchWorker("", worker.getUserId(), modelMap);

    }


    @RequestMapping("/dates")
    public String dates(@RequestParam("method") String method, @RequestParam("id") int id, ModelMap modelMap) {


        Date today = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);

        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);

        Date lastDayOfMonth = calendar.getTime();

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String thisDay = dateFormat.format(today);
        String lastDay = dateFormat.format(lastDayOfMonth);

        modelMap.addAttribute("id", id);
        modelMap.addAttribute("method", method);
        modelMap.addAttribute("thisDay", thisDay);
        modelMap.addAttribute("lastDay", lastDay);


        return "print";
    }

    @RequestMapping("/datesTwo")
    public String datesTwo(@RequestParam("method") String method, @RequestParam("id") int id, ModelMap modelMap) {


        Date today = new Date();

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String thisDay = dateFormat.format(today);


        modelMap.addAttribute("id", id);
        modelMap.addAttribute("method", method);
        modelMap.addAttribute("thisDay", thisDay);


        return "printTwo";
    }


    @RequestMapping("/printAllBeginning")
    public String printAllBeginning(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, @RequestParam("id") int id, ModelMap modelMap) {
        List<Worker> workers = workerRepository.findWorkers(id, true);

        workerService.printAllBeginning(startDate, endDate, workers);

        emailUtil.sendFile(workers, "start");

        modelMap.addAttribute("userId", id);

        return "action";

    }

    @RequestMapping("/printBeginning")
    public String printBeginning(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, @RequestParam("id") int workerId, ModelMap modelMap) {

        Worker worker = workerRepository.findById(workerId).get();
        worker.setStartDate(startDate);
        workerRepository.save(worker);
        workerService.contracts(worker, startDate, endDate);

        List<Worker> workers = new ArrayList<>();
        workers.add(worker);

        emailUtil.sendFile(workers, "start");



        modelMap.addAttribute("userId", worker.getUserId());
        return "action";

    }

    @RequestMapping(value = "/checked", params = "beginning")
    public String checked(String check, @RequestParam Integer id, ModelMap modelMap) {
        modelMap.addAttribute("id", id);

        if (check == null) {
            return "check";
        }

        Date today = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);

        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);

        Date lastDayOfMonth = calendar.getTime();

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String thisDay = dateFormat.format(today);
        String lastDay = dateFormat.format(lastDayOfMonth);

        String method = "printChecked";

        modelMap.addAttribute("check", check);

        modelMap.addAttribute("method", method);
        modelMap.addAttribute("thisDay", thisDay);
        modelMap.addAttribute("lastDay", lastDay);
        return "print";

    }


    @RequestMapping("/printChecked")
    public String printChecked(@RequestParam("check") String check, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, @RequestParam("id") int id, ModelMap modelMap) {


        List<Worker> workers = workerRepository.findWorkers(id, true);

        String[] splited = check.split(",", 0);

        List<Worker> checks = new ArrayList<>();

        for (String split : splited) {
            checks.add(workers.get(Integer.parseInt(split)));
        }


        workerService.printAllBeginning(startDate, endDate, checks);

        emailUtil.sendFile(checks, "start");


        modelMap.addAttribute("userId", id);

        return "action";
    }

    @RequestMapping("/printEnd")
    public String printEnd(@RequestParam("endDate") String endDate, @RequestParam("id") int workerId, ModelMap modelMap) {

        Worker worker = workerRepository.findById(workerId).get();

        workerService.settlement(worker, endDate);

        modelMap.addAttribute("userId", worker.getUserId());
        modelMap.addAttribute("list", Long.toString(workerId));

        List<Worker> workers = new ArrayList<>();
        workers.add(worker);

        emailUtil.sendFile(workers, "end");


        return "deactivate";

    }

    @RequestMapping("/printAllEnd")
    public String printAllEnd(@RequestParam("id") int id, @RequestParam("endDate") String endDate, ModelMap modelMap) {
        List<Worker> workers = workerRepository.findWorkers(id, true);

        StringBuilder builderList = new StringBuilder();
        for (Worker worker : workers) {
            workerService.settlement(worker, endDate);
            builderList.append(worker.getId());
            builderList.append(",");
        }

        emailUtil.sendFile(workers, "end");

        modelMap.addAttribute("userId", id);
        modelMap.addAttribute("list", builderList.toString());

        return "deactivate";
    }


    @RequestMapping(value = "/checked", params = "end")
    public String checked(String check, @RequestParam("id") int id, ModelMap modelMap) {
        modelMap.addAttribute("userId", id);

        if (check == null) {
            return "check";
        }

        Date today = new Date();

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String thisDay = dateFormat.format(today);

        String method = "printCheckedEnd";

        modelMap.addAttribute("id", id);
        modelMap.addAttribute("method", method);
        modelMap.addAttribute("thisDay", thisDay);
        modelMap.addAttribute("check", check);

        return "printTwo";

    }

    @RequestMapping("/printCheckedEnd")
    public String printCheckedEnd(@RequestParam("check") String check, @RequestParam("endDate") String endDate, @RequestParam("id") int id, ModelMap modelMap) {

        List<Worker> workers = workerRepository.findWorkers(id, true);

        String[] splited = check.split(",", 0);

        List<Worker> checks = new ArrayList<>();

        for (String split : splited) {
            checks.add(workers.get(Integer.parseInt(split)));
        }
        StringBuilder builderList = new StringBuilder();
        for (Worker worker : checks) {
            workerService.settlement(worker, endDate);
            builderList.append(worker.getId());
            builderList.append(",");

        }

        emailUtil.sendFile(checks, "end");

        modelMap.addAttribute("userId", id);
        modelMap.addAttribute("list", builderList.toString());


        return "deactivate";

    }

    @RequestMapping("/deactivate")
    public String deactivate(@RequestParam("list") String list, @RequestParam("userId") int userId, @RequestParam("type") String type, ModelMap modelMap) {
        modelMap.addAttribute("userId", userId);
        if (type.equals("tak")) {

            return "action";
        }

        String[] splited = list.split(",", 0);


        for (String split : splited) {
            Worker worker = workerRepository.findById(Integer.parseInt(split)).get();
            worker.setActive(false);
            workerRepository.save(worker);

        }


        return "action";

    }

    private class NameComparator implements Comparator<Worker> {
        @Override
        public int compare(Worker left, Worker right) {
            return left.getSurname().compareTo(right.getSurname());
        }
    }
}

