package ru.miroshka.market.auth.servicies;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.miroshka.market.auth.data.Role;
import ru.miroshka.market.auth.data.User;
import ru.miroshka.market.auth.repositories.RoleRepository;
import ru.miroshka.market.auth.repositories.UserRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleDao;

    public Role getUserRole(){
        return roleDao.findByName("ROLE_USER").get();
    }


}
