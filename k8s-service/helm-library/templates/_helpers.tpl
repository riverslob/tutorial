{{/*
A default message string to be used when cheking for required value
*/}}
{{- define "helm-library.default-check-required-msg" -}}
{{- "No value found for '%s' in helm-library template" -}}
{{- end -}}

{{/*
Common labels
*/}}
{{- define "helm-library.labels" -}}
{{- $requiredMsg := include "helm-library.default-check-required-msg" . -}}
app.kubernetes.io/name: {{ .Release.Name | quote}}
app.kubernetes.io/instance: {{ .Chart.Name | quote}}
app.kubernetes.io/manged-by: {{ .Release.Service | quote}}
app.kubernetes.io/version: {{ .Chart.AppVersion | quote}}
helm.sh/chart: {{ .Chart.Name }}-{{ .Chart.Version | replace "+" "_" }}
{{- end -}}

{{/*
Selector labels
*/}}
{{- define "helm-library.selector-labels" -}}
{{- $requiredMsg := include "helm-library.default-check-required-msg" . -}}
app.kubernetes.io/name: {{ .Release.Name | quote}}
app.kubernetes.io/instance: {{ .Chart.Name | quote}}
{{- end -}}

{{/*
will merge two YAML templates and out the result
- the top context
- the template name of the overrides(destination)
- the template name of the base(source)
*/}}
{{- define "helm-library.util.merge" -}}
{{- $top := first . -}}
{{- $overrides := fromYaml (include (index . 1) $top) | default (dict ) -}}
{{- $tpl := fromYaml (include (index . 2) $top) | default (dict ) -}}
{{- toYaml (merge $overrides $tpl) -}}
{{- end -}}






