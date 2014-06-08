package br.ufrn.paciente.controller;

import java.util.Calendar;

import br.ufrn.paciente.DAO.DAOFactory;
import br.ufrn.paciente.DAO.EventoDaoInterface;
import br.ufrn.paciente.DAO.HibernateDAOFactory;
import br.ufrn.paciente.DAO.PacienteDAOInterface;
import br.ufrn.paciente.exceptions.ComunicationException;
import br.ufrn.paciente.exceptions.ControllerException;
import br.ufrn.paciente.exceptions.DAOException;
import br.ufrn.paciente.exceptions.ObjetoNuloException;
import br.ufrn.paciente.exceptions.TopicAlreadyExistsException;
import br.ufrn.paciente.exceptions.ValorInvalidoException;
import br.ufrn.paciente.model.Evento;
import br.ufrn.paciente.model.Paciente;
import br.ufrn.paciente.publish.Publish;

public class PacienteController {

	private static final DAOFactory daoFactory = new HibernateDAOFactory();
	private static final PacienteDAOInterface pacienteDAO = daoFactory
			.getPacienteDAO();
	private EventoDaoInterface eventoDAO = daoFactory
			.getEventoDAO();

	private static final String uriHub = "http://localhost:8080/hub/hub/";

	private static final String QTD_SORO = "qtdSoro", PRESSAO = "pressao",
			BATIMENTOS = "batimentos";

	private boolean ValidarPaciente(Paciente p) throws ObjetoNuloException,
			ValorInvalidoException {

		if (p == null) {
			throw new ObjetoNuloException("Paciente nulo");
		}

		/*
		 * if (p.getId() == null || p.getId() < 0) { throw new
		 * ValorInvalidoException( "o identificador do paciente é invalido!"); }
		 */

		if (p.getIdTopico() == null || p.getIdTopico().equals("")) {
			throw new ValorInvalidoException(
					"o identificador do tópico é invalido!");
		}

		if (p.getNome() == null || p.getNome().equals("")) {
			throw new ValorInvalidoException("o nome do paciento está vazio!");
		}

		if (p.getDiagnostico() == null || p.getDiagnostico().equals("")) {
			throw new ValorInvalidoException(
					"o diagnóstico do paciente é vazio!");
		}

		return true;
	}

	public void monitorarSoro(float qtdSoro, Paciente paciente)
			throws ControllerException {

		try {
			if (qtdSoro < 0 || qtdSoro > 500) {
				throw new ControllerException(
						"valor de quantidade de soro inválido");
			}
			if (ValidarPaciente(paciente) && qtdSoro < 20) {
				Publish publish = new Publish(paciente.getIdTopico(), uriHub);

				Evento evento = new Evento(QTD_SORO + "=" + qtdSoro,
						Calendar.getInstance(), paciente);
				eventoDAO.save(evento);

				publish.publish(evento.getDescricao());

			}
		} catch (ObjetoNuloException | ValorInvalidoException e) {
			throw new ControllerException(e.getMessage());
		} catch (ComunicationException e) {
			throw new ControllerException(e.getMessage());
		} catch (DAOException e) {
			throw new ControllerException(e.getMessage());
		}

	}

	public void monitorarBatimentosCardiacos(float batimentos, Paciente paciente)
			throws ControllerException {

		try {
			if (batimentos < 0) {
				throw new ControllerException(
						"valor de quantidade de batimentos/seg inválido");
			}
			if (ValidarPaciente(paciente)
					&& (batimentos < 30 || batimentos > 100)) {
				Publish publish = new Publish(paciente.getIdTopico(), uriHub);

				Evento evento = new Evento(BATIMENTOS + "=" + batimentos,
						Calendar.getInstance(), paciente);
				eventoDAO.save(evento);

				publish.publish(evento.getDescricao());
			}
		} catch (ObjetoNuloException | ValorInvalidoException e) {
			throw new ControllerException(e.getMessage());
		} catch (ComunicationException e) {
			throw new ControllerException(e.getMessage());
		} catch (DAOException e) {
			throw new ControllerException(e.getMessage());
		}

	}

	public void monitotarPressao(float pressao, Paciente paciente)
			throws ControllerException {

		try {
			if (pressao < 0 || pressao > 30) {
				throw new ControllerException("valor da pressão é inválido");
			}
			if (ValidarPaciente(paciente) && (pressao < 8 || pressao > 12)) {
				Publish publish = new Publish(paciente.getIdTopico(), uriHub);

				Evento evento = new Evento(PRESSAO + "=" + pressao,
						Calendar.getInstance(), paciente);
				eventoDAO.save(evento);

				publish.publish(evento.getDescricao());
			}
		} catch (ObjetoNuloException | ValorInvalidoException e) {
			throw new ControllerException(e.getMessage());
		} catch (ComunicationException e) {
			throw new ControllerException(e.getMessage());
		} catch (DAOException e) {
			throw new ControllerException(e.getMessage());
		}

	}

	public void cadastrarPaciente(Paciente p) throws ControllerException {
		try {
			if (ValidarPaciente(p)) {
				pacienteDAO.save(p);

				Publish publish = new Publish(p.getIdTopico(), uriHub);

				publish.createTopic();

			}
		} catch (ObjetoNuloException | ValorInvalidoException e) {
			throw new ControllerException(e.getMessage());
		} catch (DAOException e) {
			throw new ControllerException(e.getMessage());
		} catch (ComunicationException | TopicAlreadyExistsException e) {
			throw new ControllerException(e.getMessage());
		}

	}

}
