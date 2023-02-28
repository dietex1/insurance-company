package TJV.fediayar_tjv_semestral.service;

import TJV.fediayar_tjv_semestral.Exception.Excep;
import TJV.fediayar_tjv_semestral.domain.Client;
import TJV.fediayar_tjv_semestral.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;


@Service// registrace tridy jako Service komponenty (Bean) pro DI kontejner // error no beans found

public class ClientService {


    private final ClientRepository clientRepository;


    @Autowired // injection by constructor (injektaz zavislosti pomoci konstruktoru )
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client create(Client client) throws Excep {
        if (client.getFirst_name() == null || client.getLast_name() == null || client.getAge() == null || client.getIncome() == null || client.getWork_place() == null)
            throw new Excep("Fill all fields");
        if (clientRepository.existsById(client.getClient_id()))
            throw new Excep("Person already exists");
        return clientRepository.save(client);
    }

    public Client readOne(Long id) throws Excep {
        if (clientRepository.findById(id).isEmpty())
            throw new Excep("Person with this id wasn't found");
        return clientRepository.findById(id).get();
    }

    public Collection<Client> readAll() {
        return (Collection<Client>) clientRepository.findAll();
    }

    public Client update(Long id, Client client) throws Excep {
        if (clientRepository.findById(id).isEmpty())
            throw new Excep("Person with specified wasn't found");
        Client clientModify = clientRepository.findById(id).get();

        if (client.getFirst_name() != null)
            clientModify.setFirst_name(client.getFirst_name());
        else
            throw new Excep("You should fill all fields");

        if (client.getLast_name() != null)
            clientModify.setLast_name(client.getLast_name());
        else
            throw new Excep("You should fill all fields");
        if (client.getIncome() == null)
            throw new Excep("You should fill all fields");
        else
            clientModify.setIncome(client.getIncome());
        if (client.getAge() == null)
            throw new Excep("You should fill all fields");
        else
            clientModify.setAge(client.getAge());
        if (client.getWork_place() == null)
            throw new Excep("You should fill all fields");
        else
            clientModify.setWork_place(client.getWork_place());

        return clientRepository.save(clientModify);
    }

    public String delete(Long id) throws Excep {

        if (clientRepository.findById(id).isEmpty())
            throw new Excep("Person with this id wasn't found");

        clientRepository.deleteById(id);

        return "Person with id: " + id.toString() + " was  deleted";

    }
}