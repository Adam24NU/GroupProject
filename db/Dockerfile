# db/Dockerfile
FROM mysql:8.3
ENV MYSQL_ROOT_PASSWORD=example
ENV MYSQL_DATABASE=world

# Optional: Copy any custom initialization SQL scripts
COPY world.sql /docker-entrypoint-initdb.d/