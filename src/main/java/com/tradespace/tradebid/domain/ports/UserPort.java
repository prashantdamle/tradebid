package com.tradespace.tradebid.domain.ports;

import com.tradespace.tradebid.domain.model.User;

public interface UserPort {
    User getUserByUserName(String userName);
}
