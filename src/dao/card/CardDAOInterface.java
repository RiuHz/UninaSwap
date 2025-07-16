package dao.card;

import java.util.Vector;

import entity.CardEntity;

public interface CardDAOInterface {

	Vector<CardEntity> getLast30Cards();

}
