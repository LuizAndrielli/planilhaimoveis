package com.example.planilhav3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.planilhav3.entity.Imovel;

@Repository
public interface ImovelRepository extends JpaRepository<Imovel, Long>{

}
