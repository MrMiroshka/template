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
import ru.miroshka.market.auth.integrations.CartServiceIntegration;
import ru.miroshka.market.auth.repositories.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userDao;
    private final RoleService  roleService;
    private final CartServiceIntegration cartServiceIntegration;;


    public Optional<User> findByUsername(String username) {
        return userDao.findByUserName(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        User user = findByUsername(username).get();
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), mapRolesToAuthority(user.getRoles()));

    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthority(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public void createUser(User user){
        user.setRoles(List.of(roleService.getUserRole()));
        userDao.save(user);
    }

    public void reloadCarts(String from, String to){
        cartServiceIntegration.reloadCarts(from,to);
    }

}
