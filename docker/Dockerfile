# Creates MS SQL dev enviroment

# Use the official image as a parent image: https://hub.docker.com/_/microsoft-mssql-server
FROM mcr.microsoft.com/mssql/server:2019-latest

ENV ACCEPT_EULA=Y
ENV SA_PASSWORD=Test1234

# Switch user to root
USER root

# Run update and install tools that required for project
RUN apt-get update && apt-get install unzip -y

# Install and config SqlPackage
RUN wget -O sqlpackage.zip https://go.microsoft.com/fwlink/?linkid=2143497 \
    && unzip sqlpackage.zip -d /tmp/sqlpackage \
    && chmod +x /tmp/sqlpackage/sqlpackage

# Copy config file to target directory
COPY dbconf.sql /tmp/db/dbconf.sql

# Executes config
RUN ( /opt/mssql/bin/sqlservr & ) | grep -q "Service Broker manager has started" \
	&& sleep 5s \
    && /opt/mssql-tools18/bin/sqlcmd -S localhost -U 'SA' -P 'Test1234' -i /tmp/db/dbconf.sql -C \
    && pkill sqlservr \