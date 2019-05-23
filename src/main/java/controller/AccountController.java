package controller;

import dto.AccountDTO;
import dto.converter.AccountConverter;
import model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.AccountService;
import service.AuthenticationService;
import utils.SessionUtil;

import javax.ws.rs.PathParam;

@RestController("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountConverter accountConverter;

    @Autowired
    private AuthenticationService authenticationService;

    String token = authenticationService.login(SessionUtil.user.getUsername(),SessionUtil.user.getPassword());


    @GetMapping("/{id}")
    public AccountDTO getAccount(@PathParam(value = "id") Long id){
        return accountConverter.convertToAccountDTO(accountService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDTO createAccount(@RequestBody Account account) {
        return accountConverter.convertToAccountDTO(accountService.createAccount(token));
    }

    @PutMapping
    public ResponseEntity<AccountDTO> updateAccount(@RequestBody Account account) {

        Account updatedAccount= accountService.updateAccount(account);
        if (updatedAccount != null) {
            return new ResponseEntity<>(accountConverter.convertToAccountDTO(accountService.updateAccount(updatedAccount)),
                    HttpStatus.OK);

        }

        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@RequestParam Long id) {
        accountService.deleteAccountById(id);
    }

    @GetMapping("/error")
    public AccountDTO getError() throws Exception {
        throw new Exception();
    }

}