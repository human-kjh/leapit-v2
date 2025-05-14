package com.example.leapit.user;

import com.example.leapit._core.error.ex.ExceptionApi400;
import com.example.leapit._core.error.ex.ExceptionApi401;
import com.example.leapit._core.util.JwtUtil;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserResponse.DTO join(UserRequest.@Valid PersonalJoinDTO reqDTO) {
        String encPassword = BCrypt.hashpw(reqDTO.getPassword(), BCrypt.gensalt());
        reqDTO.setPassword(encPassword);

        Optional<User> userOP = userRepository.findByUsername(reqDTO.getUsername());
        if (userOP.isPresent()) throw new ExceptionApi400("중복된 유저네임이 존재합니다");

        User userPS = userRepository.save(reqDTO.toEntity());
        return new UserResponse.DTO(userPS);
    }

    @Transactional
    public UserResponse.DTO join(UserRequest.@Valid CompanyJoinDTO reqDTO) {
        String encPassword = BCrypt.hashpw(reqDTO.getPassword(), BCrypt.gensalt());
        reqDTO.setPassword(encPassword);

        Optional<User> userOP = userRepository.findByUsername(reqDTO.getUsername());
        if (userOP.isPresent()) throw new ExceptionApi400("중복된 유저네임이 존재합니다");

        User userPS = userRepository.save(reqDTO.toEntity());
        return new UserResponse.DTO(userPS);
    }

    public Map<String, Object> checkUsernameAvailable(String username) {
        Optional<User> userOP = userRepository.findByUsername(username);
        Map<String, Object> respDTO = new HashMap<>();

        if (userOP.isPresent()) {
            respDTO.put("available", false);
        } else {
            respDTO.put("available", true);
        }
        return respDTO;
    }

    public UserResponse.TokenDTO login(UserRequest.@Valid LoginDTO loginDTO) {
        User userPS = userRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new ExceptionApi401("유저네임 혹은 비밀번호가 틀렸습니다"));

        Boolean isSame = BCrypt.checkpw(loginDTO.getPassword(), userPS.getPassword());
        if (!isSame) throw new ExceptionApi401("유저네임 혹은 비밀번호가 틀렸습니다");

        // 토큰 생성
        String accessToken = JwtUtil.create(userPS);
        return UserResponse.TokenDTO.builder().accessToken(accessToken).build();
    }
}
