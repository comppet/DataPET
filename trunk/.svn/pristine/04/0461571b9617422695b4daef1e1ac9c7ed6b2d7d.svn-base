/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import java.sql.Connection;

/**
 *
 * @author Tiago Peres
 */
public abstract class JDBCDao<T> implements Dao<T> {
    private Connection conexao;
    
    public JDBCDao(Connection conn){
        conexao = conn;
    }

    @Override
    public Connection getConexao() {
        return conexao;
    }

    @Override
    public void setConexao(Connection conexao) {
        this.conexao = conexao;
    }
    
}
