package pl.engineering.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.engineering.dto.UserTransactionCountDto;
import pl.engineering.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public Page<UserTransactionCountDto> findAllUsersWithTransactionCount(Pageable pageable) {
        return userRepository.findAllWithTransactionCount(pageable);
    }

}





