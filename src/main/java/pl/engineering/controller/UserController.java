package pl.engineering.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.engineering.dto.UserTransactionCountDto;
import pl.engineering.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/transactions")
    public ResponseEntity<Page> getUsersWithTransactionCount(Pageable pageable) {
        Page<UserTransactionCountDto> usersWithTransactionCount = userService.findAllUsersWithTransactionCount(pageable);
        return ResponseEntity.ok(usersWithTransactionCount);
    }
}
