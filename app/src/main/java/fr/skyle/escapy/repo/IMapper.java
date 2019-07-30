package fr.skyle.escapy.repo;

public interface IMapper<From, To> {

    To map(From from);
}