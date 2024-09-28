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

    @GetMapping("/{companyId}")
    public CompanyModel findByCompanyId(@PathVariable Long companyId) {
        //custom exception
        return companyService.findByCompanyId(companyId);
    }

    @PutMapping("/{companyId}")
    public CompanyModel updateCompany(@PathVariable Long companyId, @RequestBody CompanyModel updatedCompany) {
        return companyService.updateCompany(companyId, updatedCompany);

    }

    @DeleteMapping("/{companyId}")
    public void deleteCompany(@PathVariable Long companyId) {
        companyService.deleteCompany(companyId);
    }

    // Weitere Endpunkte für Update, Delete usw.
}
