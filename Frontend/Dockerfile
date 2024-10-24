FROM joseluisq/static-web-server:2-debian

EXPOSE 8081:8081
COPY . /frontend
WORKDIR /frontend
CMD [ "static-web-server","-p","8081","-d","/frontend","-g","trace"]