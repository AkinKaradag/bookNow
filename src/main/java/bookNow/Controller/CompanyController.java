package bookNow.Controller;

import bookNow.Model.CompanyModel;
import bookNow.Service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping
    public CompanyModel createCompany(@RequestBody CompanyModel company) {
        return companyService.createCompany(company);
    }

    @GetMapping
    public List<CompanyModel> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @GetMapping("/{id}")
    public CompanyModel findById(@PathVariable Long id) {
        //custom exception
        return companyService.findById(id);
    }

    @PutMapping("/{id}")
    public CompanyModel updateCompany(@PathVariable Long id, @RequestBody CompanyModel updatedCompany) {
        return companyService.updateCompany(id, updatedCompany);

    }

    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
    }

    // Weitere Endpunkte für Update, Delete usw.
}
