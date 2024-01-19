package fem.myrestfulservice.controller;

import fem.myrestfulservice.bean.User;
import fem.myrestfulservice.exception.UserNotFoundException;
import fem.myrestfulservice.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/jpa/users")
@RequiredArgsConstructor
public class UserJpaController {
    private final UserRepository userRepository;

    @GetMapping
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel> retrieveUser(@PathVariable Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        EntityModel<User> entityModel = EntityModel.of(optionalUser.get());
        return ResponseEntity.ok(entityModel.add(linkTo(methodOn(this.getClass()).retrieveAllUsers()).withRel("all-users")));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        userRepository.deleteById(id);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid User user) {
        User savedUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
