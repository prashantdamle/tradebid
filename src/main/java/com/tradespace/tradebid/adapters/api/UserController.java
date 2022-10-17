package com.tradespace.tradebid.adapters.api;

import com.tradespace.tradebid.adapters.api.dto.UserDTO;
import com.tradespace.tradebid.domain.ports.UserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserPort userPort;

    @RequestMapping(value = "/api/v1/user/{userName}", method = RequestMethod.GET)
    public UserDTO getUser(@NotNull @PathVariable("userName") String userName) {
        return UserDTO.fromUser(userPort.getUserByUserName(userName));
    }

}
