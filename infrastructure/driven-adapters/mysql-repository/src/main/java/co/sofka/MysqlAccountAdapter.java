package co.sofka;

import co.sofka.config.MysqlAccountRepository;
import co.sofka.config.MysqlCustomerRepository;
import co.sofka.data.AccountEntity;
import co.sofka.data.CustomerEntity;
import co.sofka.exception.AccountNumberException;
import co.sofka.exception.InvalidAmountException;
import co.sofka.gateway.CreateRepository;
import co.sofka.gateway.DeleteRepository;
import co.sofka.gateway.GetByIdRepository;
import co.sofka.gateway.UpdateRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;

@Repository
public class MysqlAccountAdapter implements CreateRepository<Account>, DeleteRepository<Account>, GetByIdRepository<Account>, UpdateRepository<Account> {

    private final MysqlAccountRepository repository;
    private final MysqlCustomerRepository customerRepository;

    public MysqlAccountAdapter(MysqlAccountRepository repository, MysqlCustomerRepository customerRepository) {
        this.repository = repository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void create(Account account) {
        AccountEntity entity = new AccountEntity();
        CustomerEntity customer = new CustomerEntity();
        customer.setId(Integer.parseInt(account.getCustomerId()));
        entity.setNumber(account.getNumber());
        if(entity.getNumber() <=0){
            throw new AccountNumberException("The account number cant´t be 0 or negative");
        }
        entity.setAmount(account.getAmount());
        if(entity.getAmount().compareTo(BigDecimal.ZERO) < 0 ){
            throw new InvalidAmountException("The amount can´t be negative");
        }
        entity.setCustomer(customer);
        entity.setCreatedAt(LocalDate.now());
        repository.save(entity);
    }


    @Override
    public void delete(Account account) {
        AccountEntity entity = new AccountEntity();
        entity.setId(Integer.parseInt(account.getId()));
        repository.delete(entity);
    }


    @Override
    public Account getById(Account account) {
        AccountEntity entity = repository.findById(Integer.parseInt(account.getId())).get();
        return new Account(
                String.valueOf(entity.getId()),
                entity.getNumber(),
                entity.getAmount(),
                String.valueOf(entity.getCustomerId()),
                entity.getCreatedAt());
    }

    @Override
    public void update(Account account) {
        AccountEntity entity = repository.getReferenceById(Integer.parseInt(account.getId()));
        CustomerEntity customer = customerRepository.getReferenceById(Integer.parseInt(account.getCustomerId()));
        entity.setAmount(account.getAmount());
        entity.setCustomer(customer);
        repository.save(entity);
    }

}
