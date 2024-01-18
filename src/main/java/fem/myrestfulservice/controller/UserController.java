package fem.myrestfulservice.controller;

import fem.myrestfulservice.bean.User;
import fem.myrestfulservice.dao.UserDaoService;
import fem.myrestfulservice.exception.UserNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserDaoService userDaoService;

    @GetMapping
    public List<User> retrieveAllUsers() {
        return userDaoService.findAll();
    }

    @GetMapping("/{id}")
    public EntityModel<User> retrieveUser(@PathVariable Long id) {
        User user = userDaoService.findOne(id);
        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        EntityModel<User> entityModel = EntityModel.of(user);
        entityModel.add(
                linkTo(methodOn(this.getClass())
                .retrieveAllUsers())
                .withRel("all-users"));
        return entityModel;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid User user) {
        User savedUser = userDaoService.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        User user = userDaoService.delete(id);
        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
    }
}
