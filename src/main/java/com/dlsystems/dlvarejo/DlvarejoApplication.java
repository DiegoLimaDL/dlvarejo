package com.dlsystems.dlvarejo;


import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.dlsystems.dlvarejo.domain.Categoria;
import com.dlsystems.dlvarejo.domain.Cidade;
import com.dlsystems.dlvarejo.domain.Cliente;
import com.dlsystems.dlvarejo.domain.Endereco;
import com.dlsystems.dlvarejo.domain.Estado;
import com.dlsystems.dlvarejo.domain.ItemPedido;
import com.dlsystems.dlvarejo.domain.Pagamento;
import com.dlsystems.dlvarejo.domain.PagamentoComBoleto;
import com.dlsystems.dlvarejo.domain.PagamentoComCartao;
import com.dlsystems.dlvarejo.domain.Pedido;
import com.dlsystems.dlvarejo.domain.Produto;
import com.dlsystems.dlvarejo.domain.enums.EstadoPagamento;
import com.dlsystems.dlvarejo.domain.enums.TipoCliente;
import com.dlsystems.dlvarejo.repositories.CategoriaRepository;
import com.dlsystems.dlvarejo.repositories.CidadeRepository;
import com.dlsystems.dlvarejo.repositories.ClienteRepository;
import com.dlsystems.dlvarejo.repositories.EnderecoRepository;
import com.dlsystems.dlvarejo.repositories.EstadoRepository;
import com.dlsystems.dlvarejo.repositories.ItemPedidoRepository;
import com.dlsystems.dlvarejo.repositories.PagamentoRepository;
import com.dlsystems.dlvarejo.repositories.PedidoRepository;
import com.dlsystems.dlvarejo.repositories.ProdutoRepository;



@SpringBootApplication
public class DlvarejoApplication implements CommandLineRunner {

	/*
	Este método abaixo PostConstruct realiza a troca de fuso-horário do Java
	para que ele gere o XML corretamente com os horários corretos.
	*/
	@PostConstruct
	void started(){
	  TimeZone.setDefault(TimeZone.getTimeZone("TimeZone"));
	}
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itempedidoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(DlvarejoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama mesa e banho");
		Categoria cat4 = new Categoria(null, "Eletrônicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoração");
		Categoria cat7 = new Categoria(null, "Perfumaria");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));	
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Carlos Alberto", "c.a@gmail.com", "03045678989", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("40068989", "40069898"));
		
		Endereco e1 = new Endereco(null, "Rua flores", "360", "Apto 303", "Jardim das Américas", "30022053", cli1, c1);
		Endereco e2 = new Endereco(null, "Rua matos", "258", "Apto 56", "Industrial", "40052263", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itempedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
	}

}
