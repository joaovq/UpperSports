package br.com.joaovq.uppersports.data.local

import br.com.joaovq.uppersports.team.domain.model.Team
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userLocalDataSource: UserLocalDataSource) {
    fun getIsNewUser(): Flow<Boolean> = userLocalDataSource.isNewUser
    suspend fun setIsNewUser(value: Boolean) = userLocalDataSource.setIsNewUser(value)
    suspend fun setFavTeams(value: List<Team>) = userLocalDataSource.setFavTeams(value)
}