{{/* vim: set filetype=mustache: */}}
{{/*
Environment variables for web and worker containers
*/}}
{{- define "deployment.envs" }}
env:
  - name: SERVER_PORT
    value: "{{ .Values.generic-service.image.port }}"

  - name: SPRING_PROFILES_ACTIVE
    value: "{{ .Values.generic-service.env.SPRING_PROFILES_ACTIVE }}"

  - name: JAVA_OPTS
    value: "{{ .Values.generic-service.env.JAVA_OPTS }}"

{{- end -}}
