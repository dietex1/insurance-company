package TJV.fediayar_tjv_semestral.controller;

import TJV.fediayar_tjv_semestral.Exception.Excep;
import TJV.fediayar_tjv_semestral.converter.ClientConverter;
import TJV.fediayar_tjv_semestral.domain.Client;
import TJV.fediayar_tjv_semestral.dto.ClientDto;
import TJV.fediayar_tjv_semestral.dto.InsuranceDto;
import TJV.fediayar_tjv_semestral.service.ClientService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@RestController // Registers endpoints for REST API ... cant define mapping outside @RestController
@RequestMapping("/clients") // "/" by default
@CrossOrigin // Disable CORS (cross origin resource sharing) == chrome error on fetch from client (react)
public class ClientController {


    private final ClientService clientService;

    private final ClientConverter clientConverter;

    @Autowired//autowired иньекция зависимости  1 через поле через сетер или конструктор , менять поле ток в конструкторе  а в филде непраивльно нужно заново делать сборку
    //бинс это компоненты в которых удерживает обьект даного класа и даную компоненту за жихненим циклом переменный следит депенденси инжекшон контейнер котррых предоставляет спринг бут
    public ClientController(ClientService clientService, ClientConverter clientConverter) {
        this.clientService = clientService;
        this.clientConverter = clientConverter;
    }

    @PostMapping
    ClientDto createClient(@RequestBody ClientDto clientDto) throws Excep {
        Client returnedClient = clientService.create(new Client(Long.MAX_VALUE, clientDto.first_name, clientDto.last_name, clientDto.age, clientDto.income, clientDto.work_place, new HashSet<>()));
        return clientConverter.fromDomain(returnedClient);
    }

    @GetMapping("/{client_id}")
    ClientDto readOne(@PathVariable Long client_id) throws Excep {
        Client clientFromDB = clientService.readOne(client_id);
        return clientConverter.fromDomain(clientFromDB);
    }


    @GetMapping
    Collection<ClientDto> readAll() {
        return clientConverter.fromDomainMany(clientService.readAll()); //já mu vytáhnu z databáze manyMýchDomain typů a on mi vrátí ze serveru ten požadavek
    }

    @PutMapping("/{client_id}")
    ClientDto updateClient(@RequestBody ClientDto clientDto, @PathVariable Long client_id) throws Excep {
        clientService.readOne(client_id);
        Client clientDomain = clientConverter.toDomain(clientDto);
        clientService.update(client_id, clientDomain);

        return clientDto;
    }

    @DeleteMapping("/{client_id}")
    void deleteClient(@PathVariable Long client_id) throws Excep {
        clientService.delete(client_id);
    }


}

