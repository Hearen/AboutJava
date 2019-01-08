package com.worksap.morphling.raptor.dump.thread.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.worksap.morphling.raptor.dump.thread.dao.NamespaceDoRepository;
import com.worksap.morphling.raptor.dump.thread.entity.NamespaceDo;

@RestController
@RequestMapping("dump/namespace")
public class NamespaceController {
    @Autowired
    NamespaceDoRepository namespaceDoRepository;

    @PostMapping("/{envId}/{namespace}")
    public boolean changeNamespace(@PathVariable Long envId, @PathVariable String namespace) throws Exception {
        List<NamespaceDo> namespaceDo = namespaceDoRepository.findByEnvironmentUsageId(envId);
        if (namespaceDo.size() > 0) {
            namespaceDoRepository.delete(namespaceDo);
        }
        namespaceDoRepository.save(NamespaceDo.builder()
                .environmentUsageId(envId)
                .namespace(namespace).build());
        return true;
    }

    @GetMapping("/")
    public List<NamespaceDo> getAllNamespace() throws Exception {
        return namespaceDoRepository.findAll();
    }

    @GetMapping("/{envId}")
    public String getNamespace(@PathVariable Long envId) throws Exception {
        List<NamespaceDo> namespaceDos = namespaceDoRepository.findByEnvironmentUsageId(envId);
        if (namespaceDos.size() == 0) {
            return null;
        } else {
            return namespaceDoRepository.findByEnvironmentUsageId(envId).get(0).getNamespace();
        }
    }

    @DeleteMapping("/{envId}")
    public Boolean deleteNamespace(@PathVariable Long envId) throws Exception {
        List<NamespaceDo> namespaceDos = namespaceDoRepository.findByEnvironmentUsageId(envId);
        if (namespaceDos.size() == 0) {
            return false;
        } else {
            namespaceDoRepository.delete(namespaceDos);
            return true;
        }
    }

}
