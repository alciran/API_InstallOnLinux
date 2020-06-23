# API_InstallOnLinux
Projeto de controle de produção Kadesh

## Manual de utilização do git:

**Antes de começar qualquer alteração no projeto, é necessario atualizar o repositorio local:**
```
git pull
```

### Para adicionar uma nova feature no código:

Primeiramente o usuario deve criar uma **Branch** especifica
```
git checkout -b “nome da branch”
```
**O codigo principal fica na Branch MASTER.**

Pode-se verificar em qual **Branch** está trabalhando pelo comando:
```
git status
```
Após a alteração necessaria, o usuario deve realizar o commit do codigo:
```
git add .
git commit -m "Nome do Commit"
git push origin NomeDaBranch
```
E *juntar* as **Branchs**:
```
git checkout master
git merge NomeDaBranch
git push origin master
```
